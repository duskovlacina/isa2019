package company.service;

import company.domain.Invite;

public interface InviteService {
	
	Invite save(Invite invite);
	
	Invite sendInvite(Long reservationId, Long userId);
	
	Invite acceptInvite(Long inviteId);
	
	Invite declineInvite(Long inviteId);
	
	Invite findOne(Long inviteId);
	
	void sendInviteMail(Long userId, Long inviteId);
}
