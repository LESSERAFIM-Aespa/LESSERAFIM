package kr.sparta.tripmate.domain.usecase.firebaseboardrepository

import kr.sparta.tripmate.domain.model.community.CommunityEntity
import kr.sparta.tripmate.domain.repository.community.FirebaseBoardRepository

/**
 * 작성자 : 박성수
 * 게시글의 변동사항을 저장합니다.
 */
class UpdateBoardUseCase(private val repository : FirebaseBoardRepository) {
    operator fun invoke(item:CommunityEntity) = repository.updateBoard(item)
}