package com.small.business.service.position;

import java.util.List;

import com.small.business.model.user.Position;

public interface PositionService {

    public List<Position> getAllPosition();

    public Position getPositionById(Long id);
    
    public Position getPositionUserId(Long userId);

    public boolean addPosition(Position Position);

    public boolean deletePositionById(Long id);

    public boolean deleteAll();

    public boolean updatePosition(Position Position);
}
