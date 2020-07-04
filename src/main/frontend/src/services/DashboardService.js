import axios from 'axios';
import {API_URL} from "../Constants";

class DashboardService {

  getDashboardData() {
    return axios.get(API_URL + 'api/v1/dashboard');
  }
}

export default new DashboardService();
