import React, { useEffect, useState } from 'react';
import {useLocation, useParams} from 'react-router-dom';
import axios from 'axios';
import useBoardStore from "../../store/useBoardStore";

const BoardDetail: React.FC = () => {
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const boardId = queryParams.get('boardId'); // 쿼리 파라미터에서 boardId 가져오기
    const { selectedBoardId } = useBoardStore();
    console.log("Selected Board ID in Detail Page:",selectedBoardId)

    // Use boardId as needed
    const [board, setBoard] = useState<any>(null); // 상세 게시글 상태
    useEffect(() => {
        const fetchBoardDetail = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/board/detail/${selectedBoardId}`);
                setBoard(response.data); // 상세 게시글 설정
            } catch (error) {
                console.error('게시글 상세 정보를 가져오는 데 오류가 발생했습니다:', error);
            }
        };

        fetchBoardDetail();
    }, [boardId]); // boardId 변경 시 재요청

    if (!board) {
        return <p>Loading...</p>; // 데이터가 로딩 중일 때 표시
    }

    return (
        <div>
            <h1>{board.title}</h1>
            <p>작성자: {board.user ? board.user.nickName : '정보 없음'}</p>
            <p>작성일: {board.createdAt}</p>
            <p>{board.content}</p> {/* 게시글 내용 */}
        </div>
    );
};

export default BoardDetail;