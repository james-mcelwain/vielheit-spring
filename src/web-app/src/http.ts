import axios, {AxiosRequestConfig} from 'axios';


const config: AxiosRequestConfig = {
    baseURL: 'http://localhost:8080'
};

export default axios.create(config);

