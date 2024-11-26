package com.sportuniform.service;

import com.sportuniform.domain.*;
import com.sportuniform.dto.OrderRequestDto;
import com.sportuniform.repository.ItemRepository;
import com.sportuniform.repository.MemberRepository;
import com.sportuniform.repository.OrderRepository;
import com.sportuniform.repository.SizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void saveOrder(Long memberId, OrderRequestDto dto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        List<OrderItem> orderItems = dto.getOrderItems().stream()
                .map(orderItemRequestDto -> {
                    Item item = itemRepository.findById(orderItemRequestDto.getItemId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

                    ItemSize itemSize = item.getItemSizeBySize(orderItemRequestDto.getSize());
                    return OrderItem.createOrderItem(itemSize, item.getPrice(), orderItemRequestDto.getCount());
                })
                .toList();

        Order order = Order.createOrder(member, orderItems);
        orderRepository.save(order);
    }
}
