import {
  LOGIN
} from '../constants/actions';

const initState = {};

export default function reducer(state = initState, action) {
  switch (action.type) {
    case LOGIN:
      return {
        ...state,
        identity: action.payload
      }
    default:
      return state;
  }
}