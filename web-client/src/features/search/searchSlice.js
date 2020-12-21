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

export const searchBook = (title = "", author = "", isbn13 = "", topic = "", server) => dispatch => {
    let url = `${server}:8888/search/book?title=${title}&author=${author}&isbn13=${isbn13}&topic=${topic}`;
    console.log(url);
    fetch(url)
        .then(response => response.json())
        .then(data => dispatch(setBooks(data)))
        .catch(e => {
            console.log("error fetching book search");
            console.log(e);
        });
};

export const searchDvd = (title = "", director = "", releaseDate = "", topic = "", server) => dispatch => {
    let url = `${server}:8888/search/dvd?title=${title}&director=${director}&releaseDate=${releaseDate}&topic=${topic}`;
    console.log(url);
    fetch(url)
        .then(response => response.json())
        .then(data => dispatch(setDvds(data)))
        .catch(e => {
            console.log("error fetching dvd search");
            console.log(e);
        });
};

export const searchGame = (title = "", developer = "", platforms = "", topic = "", server) => dispatch => {
    let url = `${server}:8888/search/game?title=${title}&developer=${developer}&platforms=${platforms}&topic=${topic}`;
    console.log(url);
    fetch(url)
        .then(response => response.json())
        .then(data => dispatch(setGames(data)))
        .catch(e => {
            console.log("error fetching game search");
            console.log(e);
        });
};

export const loadTopics = (server) => dispatch => {
    let url = `${server}:8888/topic`;
    fetch(url)
        .then(response => response.json())
        .then(data => dispatch(setTopics(data)))
        .catch(e => {
            console.log("error fetching topics");
            console.log(e);
        });
}

export const selectBooks = state => state.search.books;
export const selectDvds = state => state.search.dvds;
export const selectGames = state => state.search.games;
export const selectTopics = state => state.search.topics;

export default searchSlice.reducer;