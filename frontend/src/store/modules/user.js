import { USER_REQUEST, USER_ERROR, USER_SUCCESS } from "../actions/user"
import Vue from "vue";
import { AUTH_LOGOUT } from "../actions/auth";


const state = {
  status: "",
  token: localStorage.getItem("token") || ""
};

const actions = {
  [USER_REQUEST]: ({ commit, dispatch }, data) => {
    return new Promise((resolve, reject) => {
      if (data === null) {
        console.log("data = null");
        commit(USER_ERROR);
        dispatch(AUTH_LOGOUT);
        reject();
      }
      else {
        commit(USER_SUCCESS, data);
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
    localStorage.setItem("token", state.token);
    Vue.set(state, "token", state.token);
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
