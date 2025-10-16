import axios from "axios";
/**
 * 배열의 rows 그룹핑
 */
export const groupByRows = (array, number) => {
    //출력 포맷 함수 : 한줄에 상품 3개씩 출력
    //const rows = []; // [ [{}, {}, {}], [{}, {}, {}], [{}] ] 2차원배열
    //[방법 1. slice 이용]
    // for (let i = 0; i < list.length; i += 3) { //누적값으로 표시해야함
    //     const row = list.slice(i, i + 3); //slice는 새로운 배열 리턴
    //     rows.push(row);
    // }

    //[방법 2. reduce 누적합 이용]
    const rows = array.reduce((acc, cur, idx) => {
        if (idx % number === 0) acc.push([cur])
        else acc[acc.length - 1].push(cur);

        return acc;
    }, []);

    return rows;
}

/**
 * axios 함수를 이용하여 데이터 가져오기
 */
export const axiosData = async (url) => {
    const response = await axios.get(url);

    return response.data;
}

/**
 * fetch함수를 이용하여 데이터 가져오기
 */
export const fetchData = async (url) => {
    const response = await fetch(url);
    const jsonData = await response.json();

    return jsonData;
}
