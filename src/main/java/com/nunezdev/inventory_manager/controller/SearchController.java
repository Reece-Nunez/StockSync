
@RestController
@RequestMapping("api/search")
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public List<Object> search(@RequestParam("term") String searchTerm) {
        return searchService.searchAll(searchTerm);
    }
}