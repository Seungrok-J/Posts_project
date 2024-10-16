import React, {useEffect, useState} from 'react';
import {useLocation, useParams, useNavigate} from 'react-router-dom';
import axios from 'axios';
import useUserStore from "../../store/useUserStore";
import useBoardStore from "../../store/useBoardStore";
import {
    Container,
    Typography,
    Button,
    Box,
} from '@mui/material';

const BoardDetail: React.FC = () => {
    const {selectedBoardId} = useBoardStore();
    const {isLoggedIn, user} = useUserStore();
    const navigate = useNavigate();
    const {id: boardId} = useParams();

    const [board, setBoard] = useState<any>(null); // 상세 게시글 상태
    const dir_url ='C:/Users/bnosoft/1007/Posts_project/src/img';
    useEffect(() => {
        const fetchBoardDetail = async (id: string | undefined) => {
            if (id) {
                try {
                    const response = await axios.get(`http://localhost:8080/api/board/detail/${boardId}`);
                    setBoard(response.data); // 상세 게시글 설정
                } catch (error) {
                    console.error('게시글 상세 정보를 가져오는 데 오류가 발생했습니다:', error);
                }
            }
        };

        fetchBoardDetail(boardId); // boardId 또는 selectedBoardId로 게시글 상세 정보 요청
    }, [selectedBoardId]);

    if (!board) {
        return <p>Loading...</p>; // 데이터가 로딩 중일 때 표시
    }

    const handleEdit = () => {
        navigate(`/board/update/${boardId}`); // 수정 페이지로 이동
    };

    const handleDelete = async () => {
        if (window.confirm('정말로 이 게시글을 삭제하시겠습니까?')) {
            try {
                await axios.delete(`http://localhost:8080/api/board/delete/${boardId}`);
                alert('게시글이 삭제되었습니다.');
                navigate('/board/list'); // 게시글 목록으로 이동
            } catch (error) {
                console.error('게시글 삭제 중 오류 발생:', error);
                alert('게시글 삭제에 실패했습니다.');
            }
        }
    };

    return (
        <Container maxWidth="md" sx={{mt: 4}}>
            <Typography variant="subtitle2" color="textSecondary">
                카테고리: {board.category.cateName} {/* 날짜 형식 설정 */}
            </Typography>
            <Typography variant="h4" component="h1" gutterBottom>
                {board.title}
            </Typography>
            <Typography variant="subtitle1">
                작성자: {board.user ? board.user.nickName : '정보 없음'}
            </Typography>
            <Typography variant="subtitle2" color="textSecondary">
                작성일: {new Date(board.createdAt).toLocaleDateString()} {/* 날짜 형식 설정 */}
            </Typography>

            <div>
                <img src={`${dir_url}/${board.fileName}`} alt={board.title}/>
            </div>

            <Typography variant="body1" sx={{mt: 2}}>

                {board.content} {/* 게시글 내용 */}
            </Typography>

            {isLoggedIn && board.user && user && board.user.userId === user.userId && ( // 수정 및 삭제 버튼 조건
                <Box sx={{mt: 2}}>
                    <Button variant="contained" onClick={handleEdit} sx={{mr: 2}}>
                        수정
                    </Button>
                    <Button variant="contained" onClick={handleDelete}>
                        삭제
                    </Button>
                </Box>
            )}
        </Container>
    );
};

export default BoardDetail;