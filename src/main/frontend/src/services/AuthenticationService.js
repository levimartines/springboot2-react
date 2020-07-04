import axios from "axios";
import {API_URL} from "../Constants";

export const LOGIN_SESSION = 'authUser';

class AuthenticationService {

  registerSuccessfulLogin(username, token) {
    localStorage.setItem(LOGIN_SESSION, username);
    localStorage.setItem('token', token);
    this.setupAxiosInterceptors(token);
  }

  tryLogin(username, password) {
    let login = {login: username, password};
    return axios.post(API_URL + "login", login);
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem(LOGIN_SESSION);
  }

  isUserLoggedIn() {
    let user = localStorage.getItem(LOGIN_SESSION);
    return user !== null;
  }

  setupAxiosInterceptors() {
    axios.interceptors.request.use(config => {
      let token = localStorage.getItem('token');
      if (this.isUserLoggedIn() && token !== null) {
        config.headers.Authorization = token;
      }
      return config;
    })
  }

}

export default new AuthenticationService();
