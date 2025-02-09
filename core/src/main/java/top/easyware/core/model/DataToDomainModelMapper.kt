package top.easyware.core.model

interface DataToDomainModelMapper<R: DataModel, U: DomainModel> {
    fun mapToDomainModel(dataModel: R?) : U?
}