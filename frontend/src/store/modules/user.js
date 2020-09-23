import {USER_REQUEST, USER_ERROR, USER_SUCCESS} from "../actions/user"
import Vue from "vue";
import { AUTH_LOGOUT } from "../actions/auth";


const state = {
    status: "",
    userno: localStorage.getItem("user-login") || "0",
    isLoggedIn: localStorage.getItem("isLoggedIn") || "",
    token: localStorage.getItem("token") || ""
};

const actions = {
    [USER_REQUEST] : ({commit, dispatch}, data) =>{
        return new Promise((resolve, reject) =>{
            if(data === null){
                commit(USER_ERROR)
                dispatch(AUTH_LOGOUT)
                reject();
            }
            else{
                commit(USER_SUCCESS, data)
                resolve();
            }
        })
    }
}

const mutations = {
    [USER_REQUEST]: state => {
      state.status = "loading";
    },
    [USER_SUCCESS]: (state) => {
      state.status = "success";
    //   localStorage.setItem("user-login", response.data.userno);
    //   localStorage.setItem("token", response.data.token);
      localStorage.setItem("isLoggedIn", true);
    //   Vue.set(state, "userno", response.data.userno);
    //   Vue.set(state, "token", response.data.token);
      Vue.set(state, "isLoggedIn", true);
    },
    [USER_ERROR]: state => {
      state.status = "error";
    },
};
  
  export default {
    state,
    actions,
    mutations,
};
  
