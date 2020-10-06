import {
  AUTH_REQUEST,
  AUTH_ERROR,
  AUTH_SUCCESS,
  AUTH_LOGOUT
} from "../actions/auth";

import { USER_REQUEST } from "../actions/user";
import axios from "axios";

const state = {
  token: localStorage.getItem("token") || "",
  status: "",
  hasLoadedOnce: false
};

const getters = {
  isAuthenticated: state => !!state.token,
  authStatus: state => state.status
};

const actions = {
  [AUTH_REQUEST]: ({ commit, dispatch }, user) => {
    return new Promise((resolve, reject) => {
      commit(AUTH_REQUEST);
      axios(
        {
          method: "POST",
          url: "http://j3b202.p.ssafy.io:8088/dictionary/user/login",
          // url: "http://localhost:8088/dictionary/user/login",
          data: {
            email: user.email,
            password: user.password
          }
        }
      )
        .then(async response => {
          let data = response.data.object;
          commit(AUTH_SUCCESS, response);
          await dispatch(USER_REQUEST, data);
          resolve(response.data.object);
        })
        .catch(error => {
          alert("로그인 실패");
          commit(AUTH_ERROR, error);
          localStorage.removeItem("token");
          reject(error);
        })
    })
  },
  [AUTH_LOGOUT]: ({ commit }) => {
    return new Promise(resolve => {
      commit(AUTH_LOGOUT);
      localStorage.clear();
      resolve();
    })
  }
}

const mutations = {
  [AUTH_REQUEST]: state => {
    state.status = "loading";
  },
  [AUTH_SUCCESS]: state => {
    state.status = "success";
    state.hasLoadedOnce = true;
  },
  [AUTH_ERROR]: state => {
    state.status = "error";
    state.hasLoadedOnce = true;
  },
  [AUTH_LOGOUT]: state => {
    state.token = "";
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
};
