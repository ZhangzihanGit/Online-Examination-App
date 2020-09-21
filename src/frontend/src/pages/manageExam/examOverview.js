import React from 'react';
import { Link, useRouteMatch, } from 'react-router-dom';
import { List, Card } from 'antd';
import ExamDemo from "./examDemo";



export default class ExamOverview extends React.Component {
    constructor(props) {
        super();
        this.state = {
            isLoaded: false,
            examList: null
        };
    }


    componentDidMount = () => {

        const list = ExamDemo;


        this.setState({
            examList: list,
            isLoaded: true
        });

    }


    handleStart = () => {

        this.setState({
            isManaging: false,
            isStarted: true
        });
    }


    handleManage = () => {
        this.setState({
            isstarted: false,
            isManaging: true
        });
    }

    render() {
        const { isLoaded, examList } = this.state;

        if (isLoaded === false) {
            return (
                <div>
                    <h1>Loading...</h1>
                </div >
            );
        } else {

            return (
                <div>
                    <List
                        size="small"

                        dataSource={examList}
                        renderItem={(item) => (
                            <List.Item>
                                {/* <Link to={`${url}/${item.code}`}> */}
                                <Card hoverable size="small">
                                    <Card.Meta
                                        title={item.examid}
                                        description={item.description}
                                    />
                                </Card>
                                {/* </Link> */}
                            </List.Item>
                        )}
                    />
                </div >
            );
        }

    }
}