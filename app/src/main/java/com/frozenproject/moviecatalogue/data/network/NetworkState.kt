package com.frozenproject.moviecatalogue.data.network


enum class Status{
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(var status: Status, val msg: String) {

    companion object{

        val LOADED: NetworkState = NetworkState(Status.SUCCESS, "Success")
        val LOADING: NetworkState = NetworkState(Status.RUNNING, "Running")
        val ERROR: NetworkState = NetworkState(Status.FAILED, "Something went wrong")
        val END_LIST: NetworkState = NetworkState(Status.FAILED, "You have reached the end")

    }

}