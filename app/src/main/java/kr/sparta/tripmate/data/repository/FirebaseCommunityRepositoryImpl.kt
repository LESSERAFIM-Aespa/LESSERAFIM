import androidx.lifecycle.MutableLiveData
import kr.sparta.tripmate.data.datasource.remote.FirebaseDBRemoteDataSource
import kr.sparta.tripmate.data.model.community.CommunityModel
import kr.sparta.tripmate.domain.model.firebase.CommunityModelEntity
import kr.sparta.tripmate.domain.model.firebase.KeyModelEntity
import kr.sparta.tripmate.domain.repository.FirebaseCommunityRepository

class FirebaseCommunityRepositoryImpl(
    private val
    remoteSource: FirebaseDBRemoteDataSource
) : FirebaseCommunityRepository {
    override fun getCommunityData(
        uid: String,
        commuLiveData: MutableLiveData<List<CommunityModelEntity?>>,
        keyLiveData: MutableLiveData<List<KeyModelEntity?>>
    ) {
        remoteSource.getCommunityData(uid, commuLiveData, keyLiveData)
    }

    override fun updateCommuIsLike(
        model: CommunityModel,
        position: Int,
        commuLiveData: MutableLiveData<List<CommunityModelEntity?>>,
        keyLiveData: MutableLiveData<List<KeyModelEntity?>>,
        uid: String
    ) {
        remoteSource.updateCommuIsLike(model, position, commuLiveData, keyLiveData, uid)
    }

    override fun updateCommuView(
        model: CommunityModel,
        position: Int,
        commuLiveData: MutableLiveData<List<CommunityModelEntity?>>
    ) {
        remoteSource.updateCommuView(model, position, commuLiveData)
    }
}