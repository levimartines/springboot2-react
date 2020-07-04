import React, {Component} from "react";
import {ErrorMessage, Field, Form, Formik} from "formik"
import moment from "moment";
import TodoService from "../../services/TodoService";
import "./TodoComponent.css";

class TodoComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: this.props.match.params.id,
      description: 'New Todo Description',
      dueDate: moment(new Date()).format('YYYY-MM-DD')
    }

    this.onSubmit = this.onSubmit.bind(this);
    this.validate = this.validate.bind(this);
  }

  componentDidMount() {
    if (this.state.id === '0') {
      return;
    }
    TodoService.getTodo(this.state.id).then(
      res => this.setState({
        description: res.data.description,
        dueDate: moment(res.data.dueDate).format('YYYY-MM-DD'),
      })
    );
  }

  onSubmit(values) {
    let todo = {
      description: values.description,
      dueDate: values.dueDate
    };

    if (this.state.id === '0') {
      this.addTodo(todo);
    } else {
      this.updateTodo(todo);
    }
  }

  addTodo(todo) {
    TodoService.addTodo(todo)
    .then(() => this.props.history.push('/todos'));
  }

  updateTodo(todo) {
    TodoService.updateTodo(this.state.id, todo)
    .then(() => this.props.history.push('/todos'));
  }

  validate(values) {
    let errors = {};

    if (!values.description) {
      errors.description = 'Enter a description';
    } else if (values.description.length < 5) {
      errors.description = 'Enter at least 5 characters';
    }

    if (!moment(values.dueDate).isValid()) {
      errors.dueDate = 'Enter a valid date';
    }

    return errors;
  }

  render() {
    let {description, dueDate, id} = this.state;

    return (
      <div>
        {id !== '0' && <h3>Todo Component ID: {this.props.match.params.id}</h3>}
        <br/>
        <div className="form-body">
          <Formik
            className="form"
            onSubmit={this.onSubmit}
            initialValues={{description, dueDate}}
            enableReinitialize={true}
            validate={this.validate}
            validateOnBlur={false}
            validateOnChange={false}>
            {(props) => (
              <Form>

                <ErrorMessage
                  name="description" component="div"
                  className="alert alert-warning"/>
                <fieldset className="form-group">
                  <label>Description</label>
                  <Field
                    className="form-control"
                    type="text"
                    name="description"/>
                </fieldset>

                <ErrorMessage
                  name="dueDate" component="div"
                  className="alert alert-warning"/>
                <fieldset className="form-group">
                  <label>Target Date</label>
                  <Field
                    className="form-control"
                    type="date"
                    name="dueDate"/>
                </fieldset>
                <button className="btn" type="submit">Save</button>
              </Form>
            )}
          </Formik>
        </div>
      </div>
    );
  }
}

export default TodoComponent;
