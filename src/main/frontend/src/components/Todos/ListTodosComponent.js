import React, {Component} from "react";
import moment from 'moment';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faEdit} from '@fortawesome/free-solid-svg-icons'
import './ListTodosComponent.css';
import TodoService from "../../services/TodoService";

class ListTodosComponent extends Component {

  constructor(props) {
    super(props);
    this.state = {
      todoList: [],
      message: null,
    }

    this.getTodoList = this.getTodoList.bind(this);
    this.handleAddTodo = this.handleAddTodo.bind(this);

  }

  componentDidMount() {
    this.getTodoList();
  }

  render() {
    return (
      <div className="listTodosComponent">
        <h4>Todo List</h4>
        {
          this.state.message && <div
            className="alert alert-success">
            {this.state.message}
          </div>
        }
        <table className="table">
          <thead className="thead-dark">
          <tr>
            <th>ID</th>
            <th>Description</th>
            <th>Target Date</th>
            <th>Is Completed?</th>
            <th>Delete</th>
          </tr>
          </thead>
          <tbody>
          {this.state.todoList.map(todo =>
            <tr key={todo.id}>
              <td>{todo.id}</td>
              <td>{todo.description}&nbsp;&nbsp;
                <FontAwesomeIcon
                  className="icon"
                  icon={faEdit}
                  onClick={() => this.handleUpdateTodo(todo.id)}/>
              </td>
              <td>{moment(todo.dueDate).format('YYYY-MM-DD')}</td>
              <td>{todo.done.toString()}</td>
              <td>
                <button
                  className="btn deleteButton"
                  onClick={() => this.handleDeleteTodo(todo.id)}>Delete
                </button>
              </td>
            </tr>
          )}
          </tbody>
        </table>

        <div>
          <button className="btn" onClick={this.handleAddTodo}>
            +
          </button>
        </div>
      </div>
    )
  }

  getTodoList() {
    TodoService.getTodoList()
    .then(res => {
      this.setState({
        todoList: res.data
      })
    })
    .catch(err => console.log(err))
  }

  handleAddTodo() {
    this.props.history.push(`/todos/0`);
  }

  handleUpdateTodo(id) {
    this.props.history.push(`/todos/${id}`);
  }

  handleDeleteTodo(id) {
    TodoService.deleteTodo(id)
    .then(res => {
      this.setState({message: `Delete of todo ${id} successful!`});
      this.getTodoList();
    })
    .catch(err => console.log(err.data));
  }
}

export default ListTodosComponent;
