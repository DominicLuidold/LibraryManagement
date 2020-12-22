import { Button, Col, Row, Spinner, Table } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { Link, useParams } from "react-router-dom";
import { selectServer } from "../../../optionsSlice";
import { loadTopics, selectTopics } from "../../search/searchSlice";
import { loadGameDetail, selectGameDetail } from "../detailSlice";

function Game() {
    let { id } = useParams();
    const gameDetail = useSelector(selectGameDetail);
    const topics = useSelector(selectTopics);
    const server = useSelector(selectServer);
    const dispatch = useDispatch();

    if (gameDetail?.details.id !== id) {
        dispatch(loadGameDetail(id, server));
    }

    if (topics.length === 0) {
        dispatch(loadTopics(server));
    }

    return (
        <>
            <Row>
                <Col>
                    {!gameDetail &&
                        <Spinner animation="border" />
                    }
                    <h1>{gameDetail?.details.title}</h1>
                </Col>
            </Row>
            <Row>
                <Col xs={12} lg={4}>
                    <Table borderless={true}>
                        <tbody>
                            <tr>
                                <td><b>Developer</b></td>
                                <td>{gameDetail?.details.developer}</td>
                            </tr>
                            <tr>
                                <td><b>Publisher</b></td>
                                <td>{gameDetail?.details.publisher}</td>
                            </tr>
                            <tr>
                                <td><b>Platforms</b></td>
                                <td>{gameDetail?.details.platforms}</td>
                            </tr>
                            <tr>
                                <td><b>Age Restriction</b></td>
                                <td>{gameDetail?.details.ageRestriction}</td>
                            </tr>
                            <tr>
                                <td><b>Release</b></td>
                                <td>{gameDetail?.details.releaseDate[0]}</td>
                            </tr>
                            <tr>
                                <td><b>Topic</b></td>
                                <td>{topics.find(t => t.id === gameDetail?.details.topic)?.name}</td>
                            </tr>
                        </tbody>
                    </Table>

                    <Link to={`/`}><Button>Back</Button></Link>
                </Col>
                <Col xs={12} lg={8}>
                    <Table>
                        <thead>
                            <tr>
                                <th>Copy ID</th>
                                <th>Lend Till</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                gameDetail?.copies?.map(copy => {
                                    return (
                                        <tr key={copy.id} style={copy.available ? { background: "#68c17c" } : { background: "#e6717c" }}>
                                            <td>{copy.id}</td>
                                            <td>{copy.available || !copy.lendTill ? "-" : `${copy.lendTill[0]}-${copy.lendTill[1]}-${copy.lendTill[2]}`}</td>
                                        </tr>
                                    )
                                })
                            }

                        </tbody>
                    </Table>

                </Col>
            </Row>
        </>
    );
}

export default Game;