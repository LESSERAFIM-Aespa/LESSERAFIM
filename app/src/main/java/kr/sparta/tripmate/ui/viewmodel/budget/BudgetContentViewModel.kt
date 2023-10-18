package kr.sparta.tripmate.ui.viewmodel.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.sparta.tripmate.data.model.budget.Budget
import kr.sparta.tripmate.data.model.budget.BudgetCategories
import kr.sparta.tripmate.data.model.budget.Category
import kr.sparta.tripmate.domain.repository.BudgetRepository
import kr.sparta.tripmate.ui.budget.BudgetContentType

class BudgetContentViewModel(
    private val entryType: BudgetContentType,
    private val repository: BudgetRepository,
    private val budgetNum: Int = 0,
) : ViewModel() {
    private val _budgetCategories: MutableLiveData<List<BudgetCategories>> = MutableLiveData()
    val budgetCategories: LiveData<List<BudgetCategories>>
        get() = _budgetCategories

    init {
        when (entryType) {
            BudgetContentType.EDIT -> {
                viewModelScope.launch {
                    _budgetCategories.value = repository.getBudgetCategories(budgetNum)
                }
            }

            else -> {}
        }
    }

    fun inserBudgetAndCategories(budget: Budget, categories: List<Category>) =
        viewModelScope.launch {
            repository.insertBudgets(budget)
            val currentNum = repository.getLastBudget().first().num
            val arr = categories.map { it.copy(budgetNum = currentNum) }.toTypedArray()
            repository.insertCategories(*arr)
        }

    fun updateBudgetAndCategories(budget: Budget, categories: List<Category>) =
        viewModelScope.launch {
            repository.updateBudgets(budget)
            val beforeCategories = budgetCategories.value.orEmpty().first().categories.orEmpty()
            val checkedArr = BooleanArray(beforeCategories.size) { false }
            after@ for (category in categories) {
                for ((idx, beforeCategory) in beforeCategories.withIndex()) {
                    if (!checkedArr[idx] && category.num == beforeCategory.num) {
                        repository.updateCategories(category)
                        checkedArr[idx] = true
                        continue@after
                    }
                }
                repository.insertCategories(category)
            }
            val etcNum = beforeCategories[2].num // 기타 카테고리
            checkedArr.forEachIndexed { index, b ->
                if (!b){
                    val currentCategory = categories[index]
                    val procedures = repository.getProceduresWithNum(currentCategory.num).map { it.copy(categoryNum = etcNum) }.toTypedArray()
                    repository.updateProcedures(*procedures)
                    repository.deleteCategories(currentCategory)
                }
            }
        }

}