import { configureStore } from '@reduxjs/toolkit';
import counterReducer from '../features/counter/counterSlice';
import searchReducer from "../features/search/searchSlice";

export default configureStore({
    reducer: {
        counter: counterReducer,
        search: searchReducer,
    },
});