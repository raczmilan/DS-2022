import React from 'react';
import APIResponseErrorMessage from "../commons/errorhandling/api-response-error-message";
import {
    Button,
    Table,
    CardHeader,
    Col,
    Modal,
    ModalBody,
    ModalHeader,
    Row
} from 'reactstrap';
import UserInsertForm from "./components/userInsert-form";
import UserDeleteForm from "./components/userDelete-form";
import UserUpdateForm from "./components/userUpdate-form";

import * as API_USERS from "./api/user-api"
import UserTable from "./components/user-table";



class UserContainer extends React.Component {

    constructor(props) {
        super(props);
        this.toggleInsertForm = this.toggleInsertForm.bind(this);
        this.toggleDeleteForm = this.toggleDeleteForm.bind(this);
        this.toggleUpdateForm = this.toggleUpdateForm.bind(this);
        this.reload = this.reload.bind(this);
        this.state = {
            selected: false,
            insert:false,
            update:false,
            delete:false,
            collapseForm: false,
            tableData: [],
            isLoaded: false,
            errorStatus: 0,
            error: null
        };
    }

    componentDidMount() {
        this.fetchUsers();
    }

    fetchUsers() {
        return API_USERS.getUsers((result, status, err) => {

            if (result !== null && status === 200) {
                this.setState({
                    tableData: result,
                    isLoaded: true
                });
            } else {
                this.setState(({
                    errorStatus: status,
                    error: err
                }));
            }
        });
    }

    toggleInsertForm() {
        this.setState({insert: !this.state.insert});
    }

    toggleDeleteForm() {
        this.setState({delete: !this.state.delete});
    }

    toggleUpdateForm() {
        this.setState({update: !this.state.update});
    }

    reload() {
        this.setState({
            isLoaded: false
        });
        this.fetchUsers();
    }

    render() {
        return (
            <div>
                <CardHeader>
                    <strong> User Management </strong>
                </CardHeader>
                <Table >
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            <Button style={{marginRight: 1 + 'em'}} color="primary" onClick={this.toggleInsertForm}>Add User </Button>
                            <Button style={{marginRight: 1 + 'em'}} color="primary" onClick={this.toggleDeleteForm}>Delete User </Button>
                            <Button color="primary" onClick={this.toggleUpdateForm}>Update User </Button>
                        </Col>
                    </Row>
                    <br/>
                    <Row>
                        <Col sm={{size: '8', offset: 1}}>
                            {this.state.isLoaded && <UserTable tableData = {this.state.tableData}/>}
                            {this.state.errorStatus > 0 && <APIResponseErrorMessage
                                errorStatus={this.state.errorStatus}
                                error={this.state.error}
                            />   }
                        </Col>
                    </Row>
                </Table>

                <Modal isOpen={this.state.insert} toggle={this.toggleInsertForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Add User: </ModalHeader>
                    <ModalBody>
                        <UserInsertForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.delete} toggle={this.toggleDeleteForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Delete User: </ModalHeader>
                    <ModalBody>
                        <UserDeleteForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

                <Modal isOpen={this.state.update} toggle={this.toggleUpdateForm}
                       className={this.props.className} size="lg">
                    <ModalHeader toggle={this.toggleForm}> Update User: </ModalHeader>
                    <ModalBody>
                        <UserUpdateForm reloadHandler={this.reload}/>
                    </ModalBody>
                </Modal>

            </div>
        )

    }
}


export default UserContainer;
