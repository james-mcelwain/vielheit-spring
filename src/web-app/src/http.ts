import axios, {AxiosRequestConfig} from 'axios';


const config: AxiosRequestConfig = {
    baseURL: 'localhost:8080'
};

export default axios.create(config);

