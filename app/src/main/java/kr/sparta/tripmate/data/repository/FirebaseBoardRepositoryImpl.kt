package kr.sparta.tripmate.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import kr.sparta.tripmate.data.datasource.remote.FirebaseBoardRemoteDataSource
import kr.sparta.tripmate.domain.model.firebase.BoardKeyModelEntity
import kr.sparta.tripmate.domain.model.firebase.CommunityModelEntity
import kr.sparta.tripmate.domain.model.firebase.KeyModelEntity
import kr.sparta.tripmate.domain.repository.FirebaseBoardRepository

/**
 * 작성자: 서정한
 * 내용: 커뮤니티 게시판 Repository
 * */
class FirebaseBoardRepositoryImpl(
    private val
    remoteSource: FirebaseBoardRemoteDataSource
) : FirebaseBoardRepository {
    override fun getFirebaseBoardData(
        uid: String,
        boardLiveData: MutableLiveData<List<CommunityModelEntity?>>,
        likeKeyLiveData: MutableLiveData<List<KeyModelEntity?>>
    ) {
        remoteSource.getFirebaseBoardData(uid, boardLiveData, likeKeyLiveData)
    }


    override fun saveFirebaseBoardData(
        model: CommunityModelEntity,
        commuLiveData: MutableLiveData<List<CommunityModelEntity?>>
    ) {
        remoteSource.saveFirebaseBoardData(model, commuLiveData)
    }

    override fun getFirebaseLikeData(
        uid: String,
        communityData: List<CommunityModelEntity?>,
        communityLiveData: MutableLiveData<List<CommunityModelEntity?>>,
        likeKeyLiveData: MutableLiveData<List<KeyModelEntity?>>
    ) {
        remoteSource.getFirebaseLikeData(uid, communityData, communityLiveData, likeKeyLiveData)
    }

    override fun getFirebaseBookMarkData(
        uid: String,
        boardKeyLiveData: MutableLiveData<List<BoardKeyModelEntity?>>
    ) {
        remoteSource.getFirebaseBookMarkData(uid, boardKeyLiveData)
    }

    override fun saveFirebaseLikeData(
        model: CommunityModelEntity,
        commuLiveData: MutableLiveData<List<CommunityModelEntity?>>,
        keyLiveData: MutableLiveData<List<KeyModelEntity?>>,
        uid: String
    ) {
        remoteSource.saveFirebaseLikeData(model, commuLiveData, keyLiveData, uid)
    }

    override fun saveFirebaseBookMarkData(
        model: CommunityModelEntity, uid: String, context: Context, communityLiveData:
        MutableLiveData<List<CommunityModelEntity?>>, boardKeyLiveData:
        MutableLiveData<List<BoardKeyModelEntity?>>
    ) {
        remoteSource.saveFirebaseBookMarkData(model, uid, context, communityLiveData,
            boardKeyLiveData)
    }

    override fun updateCommunityWrite(item: CommunityModelEntity) {
        remoteSource.updateCommunityWrite(item)
    }

    override fun getCommunityKey(): String =
        remoteSource.getCommunityKey()


}