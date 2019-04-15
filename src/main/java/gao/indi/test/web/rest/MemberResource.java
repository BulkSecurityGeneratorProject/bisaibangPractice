package gao.indi.test.web.rest;
import gao.indi.test.domain.Member;
import gao.indi.test.repository.MemberRepository;
import gao.indi.test.web.rest.errors.BadRequestAlertException;
import gao.indi.test.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Member.
 */
@RestController
@RequestMapping("/api")
public class MemberResource {

    private final Logger log = LoggerFactory.getLogger(MemberResource.class);

    private static final String ENTITY_NAME = "member";

    private final MemberRepository memberRepository;

    public MemberResource(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * POST  /members : Create a new member.
     *
     * @param member the member to create
     * @return the ResponseEntity with status 201 (Created) and with body the new member, or with status 400 (Bad Request) if the member has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/members")
    public ResponseEntity<Member> createMember(@RequestBody Member member) throws URISyntaxException {
        log.debug("REST request to save Member : {}", member);
        if (member.getId() != null) {
            throw new BadRequestAlertException("A new member cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Member result = memberRepository.save(member);
        return ResponseEntity.created(new URI("/api/members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    @PostMapping("/bulkRregistration")
    public boolean createMembers(@RequestBody List<Member> members) throws URISyntaxException {
        log.debug("REST request to save Member : {}", members);
        for (Member member : members
        ) {
            if (member.getId() != null) {
                throw new BadRequestAlertException("A new member cannot already have an ID", ENTITY_NAME, "idexists");
            }
            Member result = memberRepository.save(member);
        }
        return true;
    }
    /**
     * PUT  /members : Updates an existing member.
     *
     * @param member the member to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated member,
     * or with status 400 (Bad Request) if the member is not valid,
     * or with status 500 (Internal Server Error) if the member couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/members")
    public ResponseEntity<Member> updateMember(@RequestBody Member member) throws URISyntaxException {
        log.debug("REST request to update Member : {}", member);
        if (member.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Member result = memberRepository.save(member);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, member.getId().toString()))
            .body(result);
    }

    /**
     * GET  /members : get all the members.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of members in body
     */
    @GetMapping("/members")
    public List<Member> getAllMembers() {
        log.debug("REST request to get all Members");
        return memberRepository.findAll();
    }
/**
 *
 */
@GetMapping("/leader")
public List<Member> finaAllLeaders() {
    log.debug("REST request to get all leaders");
    return memberRepository.finaAllLeaders();
}

    /**
     *
     * @return
     */
    @GetMapping("/finaTeamByTeamName/{teamname}")
    public List<Member> findTeamByTeamName(@PathVariable String teamname) {
        log.debug("REST request to get teamMember:{}",teamname);
        return memberRepository.findTeamByTeamName(teamname);
    }
    @GetMapping("/teams")
    public List<String> findAllTeam() {
        log.debug("REST request to get teams");
        return memberRepository.findAllTeam();
    }
    /**
     * GET  /members/:id : get the "id" member.
     *
     * @param id the id of the member to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the member, or with status 404 (Not Found)
     */
    @GetMapping("/members/{id}")
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        log.debug("REST request to get Member : {}", id);
        Optional<Member> member = memberRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(member);
    }

    /**
     * DELETE  /members/:id : delete the "id" member.
     *
     * @param id the id of the member to delete
    * @return the ResponseEntity with status 200 (OK)
    */
@DeleteMapping("/members/{id}")
public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
    log.debug("REST request to delete Member : {}", id);
    memberRepository.deleteById(id);
    return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    }
