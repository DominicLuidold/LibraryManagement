import { createSlice } from '@reduxjs/toolkit';

export const detailSlice = createSlice({
    name: "detail",
    initialState: {
        book: undefined,
        dvd: undefined,
        game: undefined,
    },
    reducers: {
        setBook: (state, action) => {
            state.book = action.payload;
        },
        setDvd: (state, action) => {
            state.dvd = action.payload;
        },
        setGame: (state, action) => {
            state.game = action.payload;
        },
    },
});

export const { setBook, setDvd, setGame } = detailSlice.actions;

export const loadBookDetail = bookId => dispatch => {
    dispatch(setBook(null));
    let url = `***REMOVED***/detail/book/${bookId}`;
    console.log(url);
    fetch(url)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw (response.statusText);
            }
        })
        .then(data => dispatch(setBook(data)))
        .catch(e => {
            console.log("error fetching book detail");
            console.log(e);
        });
}

export const loadDvdDetail = dvdId => dispatch => {
    dispatch(setDvd(null));
    let url = `***REMOVED***/detail/dvd/${dvdId}`;
    console.log(url);
    fetch(url)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw (response.statusText);
            }
        })
        .then(data => dispatch(setDvd(data)))
        .catch(e => {
            console.log("error fetching dvd detail");
            console.log(e);
        });
}

export const selectBookDetail = state => state.detail.book;
export const selectDvdDetail = state => state.detail.dvd;

export default detailSlice.reducer;