import axios from 'axios';
import {API_URL} from "../Constants";

class SignupService {

  registerUser(user) {
    return axios.post(API_URL + 'api/v1/signup', user);
  }

}

export default new SignupService();
