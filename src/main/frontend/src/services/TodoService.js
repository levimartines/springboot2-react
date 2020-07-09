import axios from 'axios';
import {API_URL} from "../Constants";

class TodoService {

  TODO_URL = API_URL + 'api/v1/todos/';

  getTodo(id) {
    return axios.get(this.TODO_URL + id);
  }

  getTodoList() {
    return axios.get(this.TODO_URL);
  }

  addTodo(todo) {
    return axios.post(this.TODO_URL, todo);
  }

  updateTodo(id, todo) {
    return axios.put(this.TODO_URL + id, todo);
  }

  deleteTodo(id) {
    return axios.delete(this.TODO_URL + id);
  }
}

export default new TodoService();
