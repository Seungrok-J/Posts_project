import React, { useEffect, useState, CSSProperties } from 'react';
import axios from 'axios';
import { Navigation } from 'react-minimal-side-navigation';
import 'react-minimal-side-navigation/lib/ReactMinimalSideNavigation.css';
import { Category } from '../../types/Category';

interface SideBarProps {
    onCategorySelect: (cateId: number | null) => void; // 선택된 카테고리 ID를 전달하는 함수
}

const SideBar: React.FC<SideBarProps> = ({ onCategorySelect }) => {
    const [categories, setCategories] = useState<Category[]>([]);
    const [activeItemId, setActiveItemId] = useState<string>('/all');

    const fetchCategories = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/board/categories');
            setCategories(response.data);
        } catch (error) {
            console.error('카테고리 목록을 가져오는 데 오류가 발생했습니다:', error);
        }
    };

    useEffect(() => {
        fetchCategories();
    }, []);

    const handleCategorySelect = (itemId: string) => {
        setActiveItemId(itemId);
        onCategorySelect(itemId === '/all' ? null : parseInt(itemId)); // 선택한 카테고리 ID를 부모에게 전달
    };

    const barStyle: CSSProperties = {
        width: '100%',
        height: '100%',
    };

    return (
        <div style={barStyle}>
            <Navigation
                activeItemId={activeItemId}
                onSelect={({ itemId }) => handleCategorySelect(itemId)}
                items={[
                    {
                        title: '목록',
                        itemId: '/all',
                        subNav: [
                            {
                                title: 'ALL',
                                itemId: '/all',
                            },
                            ...categories.map((category) => ({
                                title: category.cateName,
                                itemId: category.cateId.toString(),
                            })),
                        ],
                    },
                ]}
            />
        </div>
    );
};

export default SideBar;