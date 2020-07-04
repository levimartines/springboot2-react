import axios from 'axios';
import {API_URL} from "../Constants";

class TodoService {


  getTodo(id) {
    return axios.get(API_URL + 'api/v1/todos/' + id);
  }

  getTodoList() {
    return axios.get(API_URL + 'api/v1/todos');
  }

  addTodo(todo){
    return axios.post(API_URL + 'api/v1/todos', todo);
  }

  updateTodo(id, todo){
    return axios.put(API_URL + 'api/v1/todos/' + id, todo);
  }

  deleteTodo(id) {
    return axios.delete(API_URL + 'api/v1/todos/' + id);
  }
}

export default new TodoService();
