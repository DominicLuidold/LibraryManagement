import { createSlice } from '@reduxjs/toolkit';

export const searchSlice = createSlice({
    name: "search",
    initialState: {
        books: [],
        dvds: [],
        games: [],
        topics: [],
    },
    reducers: {
        setBooks: (state, action) => {
            state.books = action.payload;
        },
        setDvds: (state, action) => {
            state.dvds = action.payload;
        },
        setGames: (state, action) => {
            state.games = action.payload;
        },
        setTopics: (state, action) => {
            state.topics = action.payload;
        }
    },
});

export const { setBooks, setDvds, setGames, setTopics } = searchSlice.actions;

export const searchBook = (title = "", author = "", isbn13 = "", topic = "") => dispatch => {
    let url = `***REMOVED***/search/book?title=${title}&author=${author}&isbn13=${isbn13}&topic=${topic}`;
    console.log(url);
    fetch(url)
        .then(response => response.json())
        .then(data => dispatch(setBooks(data)));
};

export const loadTopics = () => dispatch => {
    let url = "***REMOVED***/topic";
    fetch(url)
        .then(response => response.json())
        .then(data => dispatch(setTopics(data)));
}

export const selectBooks = state => state.search.books;

export const selectTopics = state => state.search.topics;

export default searchSlice.reducer;