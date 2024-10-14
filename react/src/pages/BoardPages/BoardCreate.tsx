import React, { useEffect, useState } from 'react';
import { Category } from "../../types/Category";
import axios from "axios";
import { toast } from "react-toastify";
import useUserStore from "../../store/useUserStore";

const BoardCreate = () => {
    const [postFormData, setPostFormData] = useState({
        category: {
            cateId: 0,  // cateId를 기본값으로 설정
        },
        title: '',
        content: '',
    });

    const [category, setCategory] = useState<Category[]>([]);
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const { isLoggedIn } = useUserStore();  // Zustand에서 유저 정보를 가져옴

    useEffect(() => {
        const fetchCateList = async () => {
            try {
                const response = await axios.get('http://127.0.0.1:8080/api/board/categories');
                setCategory(response.data);
            } catch (error) {
                console.log("카테고리 목록을 가져오는 데 오류가 발생했습니다.", error);
            }
        };
        fetchCateList();
    }, []);

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files) {
            setSelectedFile(event.target.files[0]);
        }
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        // 로그인 여부 확인
        if (!isLoggedIn) {
            console.log("인증이 필요합니다. 다시 로그인해 주세요.");
            return;
        }

        const formData = new FormData();
        formData.append('title', postFormData.title);
        formData.append('content', postFormData.content);
        formData.append('categoryId', postFormData.category.cateId.toString());  // 카테고리 ID 전송

        // 파일이 선택되었는지 확인 후 추가
        if (selectedFile) {
            formData.append('file', selectedFile);
        }

        // 토큰을 sessionStorage에서 가져옴
        const token = sessionStorage.getItem('token');

        try {
            const response = await axios.post('http://127.0.0.1:8080/api/board/save', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    'Authorization': `Bearer ${token}`,  // 인증 토큰 추가
                },
            });

            const boardId = response.data;  // 서버에서 반환된 게시글 ID

            // 작성된 게시글의 디테일 페이지로 이동
            window.location.href = `/board/detail/${boardId}`;

        } catch (error) {
            console.log("게시글 작성 중 오류 발생", error);
            toast.error("게시글 작성에 실패했습니다.");
        }
    };

    return (
        <form onSubmit={handleSubmit} className="mt-8 max-w-md mx-auto">
            <select
                id="cate-select"
                defaultValue="default"
                onChange={(e) => setPostFormData({ ...postFormData, category: { cateId: parseInt(e.target.value) } })}
            >
                <option value="default" disabled>
                    선택하세요.
                </option>
                {category.map((item) => (
                    <option key={item.cateId} value={item.cateId}>
                        {item.cateName}
                    </option>
                ))}
            </select>

            <label>Title: </label>
            <input
                placeholder="제목을 입력하세요."
                value={postFormData.title}
                onChange={(e) => setPostFormData({ ...postFormData, title: e.target.value })}
                required
            />

            <textarea
                placeholder="내용을 입력하세요."
                value={postFormData.content}
                onChange={(e) => setPostFormData({ ...postFormData, content: e.target.value })}
                required
            />

            <input type="file" onChange={handleFileChange} />

            <button
                className="m-4 py-2 px-4 bg-white-700 hover:bg-white-500 text-blue font-semibold rounded-lg shadow-md"
                onClick={() => window.history.back()}>
                뒤로가기
            </button>

            <button
                className="m-4 py-2 px-4 bg-blue-500 hover:bg-blue-700 text-white font-semibold rounded-lg shadow-md"
                type="submit">
                Submit
            </button>
        </form>
    );
};

export default BoardCreate;
