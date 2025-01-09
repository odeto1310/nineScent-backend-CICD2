package shop.ninescent.mall.mypage.service;

@SpringBootTest
public class MyPageServiceTest {

    @Autowired
    private MyPageService myPageService;

    @MockBean
    private AddressRepository addressRepository;

    @Test
    void testAddOrUpdateAddress() {
        // Given
        AddressDto dto = new AddressDto();
        dto.setAddrNo(1L);
        dto.setUserNo(101L);
        dto.setAddrName("Test Address");

        Address address = new Address();
        address.setAddrNo(1L);
        address.setUserNo(101L);
        address.setAddrName("Test Address");

        when(addressRepository.save(any(Address.class))).thenReturn(address);

        // When
        AddressDto result = myPageService.addOrUpdateAddress(dto);

        // Then
        assertEquals(dto.getAddrNo(), result.getAddrNo());
        assertEquals(dto.getAddrName(), result.getAddrName());
    }
}
