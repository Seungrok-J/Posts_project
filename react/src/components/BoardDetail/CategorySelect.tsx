import {Category} from "../../@types/Category";
import {FormControl, InputLabel, MenuItem, Select} from "@mui/material";


interface CategorySelectProps {
    categories: Category[];
    selectedCategory: string;
    onChange: (cateName: string) => void;
}

const CategorySelect: React.FC<CategorySelectProps> = ({categories, selectedCategory, onChange}) => (
    <FormControl fullWidth margin="normal">
        <InputLabel id="cate-select-label" >카테고리</InputLabel>
        <Select
            sx={{ marginTop: 2 }}
            labelId="cate-select-label"
            value={selectedCategory}
            onChange={(e) => onChange(e.target.value as string)}
        >
            <MenuItem value="default" disabled>선택하세요.</MenuItem>
            {categories.map((item) => (
                <MenuItem key={item.cateName} value={item.cateName}>
                    {item.cateName}
                </MenuItem>
            ))}
        </Select>
    </FormControl>
);

export default CategorySelect;