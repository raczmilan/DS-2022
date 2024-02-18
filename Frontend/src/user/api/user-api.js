import {HOST} from '../../commons/hosts';
import RestApiClient from "../../commons/api/rest-client";


const endpoint = {
    home: '/',
    users: '/users',
    insert: '/users/insert',
    delete: '/users/delete',
    update: '/users/update'
};

function getUsers(callback) {
    let request = new Request(HOST.backend_api + endpoint.users, {
        method: 'GET',
    });
    console.log(request.url);
    RestApiClient.performRequest(request, callback);
}

function postUser(user, callback){
    let request = new Request(HOST.backend_api + endpoint.home , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);

}

function postInsertUser(user, callback){
    let request = new Request(HOST.backend_api + endpoint.insert , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function postDeleteUser(user, callback){
    let request = new Request(HOST.backend_api + endpoint.delete , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

function postUpdateUser(user, callback){
    let request = new Request(HOST.backend_api + endpoint.update , {
        method: 'POST',
        headers : {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    });

    console.log("URL: " + request.url);

    RestApiClient.performRequest(request, callback);
}

export {
    getUsers,
    postUser,
    postInsertUser,
    postDeleteUser,
    postUpdateUser
};
