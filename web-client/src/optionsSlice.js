import { createSlice } from '@reduxjs/toolkit';

export const detailSlice = createSlice({
    name: "options",
    initialState: {
        server: "http://vsts-team007.westeurope.cloudapp.azure.com",
    },
    reducers: {
        setServer: (state, action) => {
            state.server = action.payload;
        },
    },
});

export const { setServer } = detailSlice.actions;

export const selectServer = state => state.options.server;

export default detailSlice.reducer;