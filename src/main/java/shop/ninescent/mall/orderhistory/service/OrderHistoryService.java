//package shop.ninescent.mall.orderhistory.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import shop.ninescent.mall.address.domain.Address;
//import shop.ninescent.mall.address.repository.AddressRepository;
//import shop.ninescent.mall.item.domain.Item;
//import shop.ninescent.mall.item.repository.ItemRepository;
//import shop.ninescent.mall.member.domain.User;
//import shop.ninescent.mall.member.repository.UserRepository;
//import shop.ninescent.mall.order.domain.OrderItems;
//import shop.ninescent.mall.order.domain.Orders;
//import shop.ninescent.mall.order.dto.OrderItemDTO;
//import shop.ninescent.mall.order.repository.OrderItemRepository;
//import shop.ninescent.mall.order.repository.OrderRepository;
//import shop.ninescent.mall.orderhistory.dto.OrderHistoryDTO;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class OrderHistoryService {
//    private final OrderRepository orderRepository;
//    private final OrderItemRepository orderItemRepository;
//    private final UserRepository userRepository;
//
//    public List<OrderHistoryDTO> getOrderHistory(Long userNo) {
//        // Retrieve all orders for the user (adjustable for date filtering)
//        List<Orders> userOrders = orderRepository.findByUserUserNo(userNo);
//
//        return userOrders.stream().map(order -> {
//            // Retrieve the items associated with the order
//            List<OrderItems> orderItems = orderItemRepository.findByOrderOrderId(order.getOrderId());
//
//            // Map order items to DTOs
//            List<OrderItemDTO> itemDTOs = orderItems.stream().map(orderItem -> {
//                Item item = orderItem.getItem(); // Ensure OrderItems is linked to Item via ManyToOne
//                Address address = order.getAddress(); // Ensure Orders is linked to Address via ManyToOne
//                User user = order.getUser();
//
//                return new OrderItemDTO(
//                        item.getItemId(),
//                        item.getItemName(),
//                        orderItem.getQuantity(),
//                        item.getPrice(),
//                        item.getDiscountedPrice(),
//                        user.getUserNo(),
//                        user.getName(),
//                        address.getAddrNo(),
//                        address.getAddrDetail(),
//                        address.getIsExtraFee() ? 5000L : 0L
//                );
//            }).collect(Collectors.toList());
//
//            // Return an OrderHistoryDTO for each order
//            return new OrderHistoryDTO(
//                    order.getOrderId(),
//                    order.getOrderDate(),
//                    order.isDeliveryDone(),
//                    order.isPaymentDone(),
//                    order.isRefundChangeDone(),
//                    itemDTOs
//            );
//        }).collect(Collectors.toList());
//    }
//}