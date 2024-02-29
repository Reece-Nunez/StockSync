package Test;

import com.nunezdev.inventory_manager.InventoryManagerApplication;
import com.nunezdev.inventory_manager.dto.ProductDTO;
import com.nunezdev.inventory_manager.entity.Product;
import com.nunezdev.inventory_manager.repository.ProductRepository;
import com.nunezdev.inventory_manager.service.ProductService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = InventoryManagerApplication.class)
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void testCreateProduct() {
        // Mocking the modelMapper to simulate the mapping
        ProductDTO productDto = new ProductDTO(null, "Test Product", "Description", 10.0, 100, null);
        Product product = new Product();
        product.setName("Test Product");
        product.setDescription("Description");
        product.setPrice(10.0);
        product.setQuantity(100);
        product.setDateCreated(LocalDate.now());

        when(modelMapper.map(productDto, Product.class)).thenReturn(product);
        when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDto);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO createdProductDto = productService.createProduct(productDto);

        assertNotNull(createdProductDto);
        assertEquals(productDto.getName(), createdProductDto.getName());
        assertNotNull(createdProductDto.getDateCreated());

        verify(modelMapper, times(1)).map(productDto, Product.class);
        verify(modelMapper, times(1)).map(product, ProductDTO.class);
        verify(productRepository, times(1)).save(any(Product.class));
    }
}
