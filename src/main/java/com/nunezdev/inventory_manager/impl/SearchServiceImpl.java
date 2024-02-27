
@Service
public class SearchServiceImpl implements SearchServce {

    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public SearchServiceImpl(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public List<Object> searchAll(String searchTerm) {
        List<Object> results = new ArrayList<>();
        results.addAll(categoryService.searchCategories(searchTerm));
        results.addAll(productService.searchProducts(searchTerm));
        return results;
    }
}