import React, {useEffect, useState} from 'react';
import {useParams, useNavigate} from 'react-router-dom';
import axios from 'axios';
import useUserStore from "../../store/useUserStore";
import {
    Container,
    Button,
    Box,
} from '@mui/material';
import BoardContent from "../../components/BoardDetail/BoardContent";

const BoardDetail: React.FC = () => {
    const {isLoggedIn, user} = useUserStore();
    const navigate = useNavigate();
    const {id: boardId} = useParams();

    const [board, setBoard] = useState<any>(null);
    const dirUrl = 'C:/Users/bnosoft/1007/Posts_project/src/img';

    useEffect(() => {
        const fetchBoardDetail = async (id: string | undefined) => {
            if (id) {
                try {
                    const response = await axios.get(`http://localhost:8080/api/board/detail/${boardId}`);
                    setBoard(response.data);
                } catch (error) {
                    console.error('게시글 상세 정보를 가져오는 데 오류가 발생했습니다:', error);
                }
            }
        };

        fetchBoardDetail(boardId);
    }, [boardId]);

    if (!board) {
        return <p>Loading...</p>;
    }

    const handleEdit = () => {
        navigate(`/board/update/${boardId}`);
    };

    const handleDelete = async () => {
        if (window.confirm('정말로 이 게시글을 삭제하시겠습니까?')) {
            try {
                await axios.delete(`http://localhost:8080/api/board/delete/${boardId}`);
                alert('게시글이 삭제되었습니다.');
                navigate('/board/list');
            } catch (error) {
                console.error('게시글 삭제 중 오류 발생:', error);
                alert('게시글 삭제에 실패했습니다.');
            }
        }
    };

    return (
        <Container maxWidth="sm" sx={{mt: 4}}>
            <BoardContent board={board} dirUrl={dirUrl}/>
            {isLoggedIn && board.user && user && board.user.userId === user.userId && (
                <Box display="flex" justifyContent="space-between" mt={2}>
                    <Box display="flex" justifyContent="space-between" mt={2}>
                        <Button variant="contained" onClick={handleEdit} sx={{mr: 2}}>
                            수정
                        </Button>
                        <Button variant="contained" onClick={handleDelete}>
                            삭제
                        </Button>
                    </Box>
                    <Box display="flex" justifyContent="flex-start" mt={2}>

                        <Button variant="outlined" sx={{mr: 2}} onClick={() => window.history.back()}>뒤로가기</Button>

                    </Box>
                </Box>
            )}
        </Container>
    );
};

export default BoardDetail;
