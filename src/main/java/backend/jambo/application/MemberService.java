package backend.jambo.application;

import backend.jambo.domain.Member;
import backend.jambo.domain.Status;
import backend.jambo.domain.request.MemberRequest;
import backend.jambo.domain.response.MemberResponse;
import backend.jambo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse save(MemberRequest request) {
        memberRepository.findById(request.id()).ifPresent(member -> {
            throw new IllegalArgumentException(String.format("해당 유저 %d는 이미 가입되어 있습니다.", request.id()));
        });

        Member member = Member.of(request.id(), request.nickname());
        memberRepository.save(member);
        return MemberResponse.of(member);
    }

    public MemberResponse findMember(long memberId) {
        return MemberResponse.of(memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalStateException(String.format("존재하지 않는 유저 %d 입니다.", memberId))));
    }

    public List<MemberResponse> findAllRegisteredMember() {
        return memberRepository.findAllRegisteredMember(Status.REGISTER)
                .stream()
                .map(MemberResponse::of)
                .toList();
    }

    @Transactional
    public MemberResponse deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 유저 %d 입니다.", memberId)));

        member.updateStatus();

        return MemberResponse.of(member);
    }
}
