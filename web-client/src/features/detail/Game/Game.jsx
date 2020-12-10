import { useParams } from "react-router-dom";

function Game() {
    let { id } = useParams();

    return (
        <h1>Detail of Game {id}</h1>
    );
}

export default Game;