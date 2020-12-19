import { configureStore } from '@reduxjs/toolkit';
import searchReducer from "../features/search/searchSlice";
import detailReducer from "../features/detail/detailSlice";

export default configureStore({
    reducer: {
        search: searchReducer,
        detail: detailReducer,
    },
});