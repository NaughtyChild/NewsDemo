package view.naughtychild.myapplication.jsonObj

data class Result(val stat: String, val data: Array<NewsData>)
data class BaseResponse(val reason: String, val result: Result)