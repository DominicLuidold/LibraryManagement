import { useParams } from "react-router-dom";

function Dvd() {
    let { id } = useParams();

    return (
        <h1>Detail of Dvd {id}</h1>
    );
}

export default Dvd;