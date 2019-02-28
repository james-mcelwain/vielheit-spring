import {action, computed, observable} from 'mobx';
import http from './http';

export class State {
    @observable
    public loggedIn = false;
    @observable
    public user: object | null = null;
    @observable
    public error: string | null = null;

    @computed
    get token() {
        return localStorage.getItem('token')
    }

    constructor() {
        this.loggedIn = !!this.token;

        if (this.loggedIn) {
            http.get(`/user`).then(({ data }) => {
                this.user = data
            })
        }
    }

    @action
    public async logIn(username: String, password: String) {
        this.resetError();

        try {
            const { data } = await http.post('/user/auth', {
                username,
                password
            });
            const { token, user } = data;
            this.loggedIn = true;
            localStorage.setItem("userId", user.id);
            localStorage.setItem('token', token);
        } catch (error) {
            this.error = error
        }
    }

    private resetError() {
        this.error = null
    }
}