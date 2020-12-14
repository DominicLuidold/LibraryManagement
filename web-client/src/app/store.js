import { configureStore } from '@reduxjs/toolkit';
import counterReducer from '../features/counter/counterSlice';
import searchReducer from "../features/search/searchSlice";
import detailReducer from "../features/detail/detailSlice";

export default configureStore({
    reducer: {
        counter: counterReducer,
        search: searchReducer,
        detail: detailReducer,
    },
});