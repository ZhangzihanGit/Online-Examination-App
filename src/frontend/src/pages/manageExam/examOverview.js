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
                        url="ads"
                        dataSource={examList}
                        renderItem={(item) => (
                            <List.Item>
                                <Card hoverable size="small" onClick={() => this.props.startUpdate(item)}>
                                    <Card.Meta
                                        title={item.showName}
                                        description={item.description}
                                    />
                                </Card>
                            </List.Item>
                        )}
                    />
                </div >
            );
        }

    }
}