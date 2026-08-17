package com.github.chocobe.sbb_mission2;

import com.github.chocobe.sbb_mission2.domain.question.entity.Question;
import com.github.chocobe.sbb_mission2.domain.question.repository.QuestionRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbMission2ApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Disabled
	@Test
	void createMockData() {
		int numOfQuestions = 300;
		IntStream.rangeClosed(1, numOfQuestions)
				.forEach(num -> {
					Question question = new Question();
					question.setSubject("[MOCK] 질문 %d".formatted(num));
					question.setContent("질문 내용 %d".formatted(num));
					question.setCreateDate(LocalDateTime.now());

					this.questionRepository.save(question);
				});
	}

	@Test
	@DisplayName("제목으로 질문 검색하기")
	void t1() {
		Question question = this.questionRepository.findBySubject("[MOCK] 질문 50").get();
		assertEquals(50, question.getId());
		assertEquals("질문 내용 50", question.getContent());
	}

	@Test
	@DisplayName("제목과 내용으로 질문 검색하기")
	void t2() {
		Question question = this.questionRepository
				.findBySubjectAndContent(
						"[MOCK] 질문 12",
						"질문 내용 12"
				).get();
		assertEquals(12, question.getId());
	}

	@Test
	@DisplayName("제목 일부 키워드로 질문 검색하기")
	void t3() {
		List<Question> questionList = this.questionRepository
				.findAllBySubjectLike("%100%");
		assertEquals(1, questionList.size());
		assertEquals(100, questionList.getFirst().getId());
		assertEquals("[MOCK] 질문 100", questionList.getFirst().getSubject());
		assertEquals("질문 내용 100", questionList.getFirst().getContent());
	}

}
