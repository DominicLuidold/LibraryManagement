import { createSlice } from '@reduxjs/toolkit';

export const searchSlice = createSlice({
    name: "search",
    initialState: {
        books: [],
        dvds: [],
        games: []
    },
    reducers: {
        setBooks: (state, action) => {
            state.books = action.payload;
        },
        setDvds: (state, action) => {
            state.dvds.push(action.payload);
        },
        setGames: (state, action) => {
            state.games.push(action.payload);
        },
    },
});

export const { setBooks, setDvds, setGames } = searchSlice.actions;

export const searchBook = (title = "", author = "", isbn13 = "", topic = "") => dispatch => {
    let url = `***REMOVED***/search/book?title=${title}&author=${author}&isbn13=${isbn13}&topic=${topic}`;
    console.log(url);
    fetch(url)
        .then(response => response.json())
        .then(data => dispatch(setBooks(data)));
};

export const selectBooks = state => state.search.books;

export default searchSlice.reducer;