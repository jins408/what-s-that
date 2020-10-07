import { USER_REQUEST, USER_ERROR, USER_SUCCESS } from "../actions/user"
import Vue from "vue";

const state = {
  status: "",
  token: localStorage.getItem("token") || "",
  userno: localStorage.getItem("userno") || ""
};

const actions = {
  [USER_REQUEST]: ({ commit }, data) => {
    return new Promise((resolve) => {
      commit(USER_SUCCESS, data);
      resolve();
    })
  }
}

const mutations = {
  [USER_REQUEST]: state => {
    state.status = "loading";
  },
  [USER_SUCCESS]: (state, resp) => {
    state.status = "success";
    localStorage.setItem("token", resp.token);
    Vue.set(state, "token", resp.token);
    localStorage.setItem("userno", resp.userno);
    Vue.set(state, "userno", resp.userno);
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