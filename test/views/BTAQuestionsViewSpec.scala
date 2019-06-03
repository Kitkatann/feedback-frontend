/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package views

import play.api.data.Form
import forms.BTAQuestionsFormProvider
import views.behaviours.{OptionsViewBehaviours, StringViewBehaviours, YesNoViewBehaviours}
import models.{BTAQuestions, HowDoYouFeelQuestion, HowEasyQuestion, MainServiceQuestion}
import views.html.btaQuestions

class BTAQuestionsViewSpec extends YesNoViewBehaviours[BTAQuestions]
  with StringViewBehaviours[BTAQuestions]
  with OptionsViewBehaviours[BTAQuestions] {

  val messageKeyPrefix = "btaQuestions"

  val form = new BTAQuestionsFormProvider()()
  val action = controllers.routes.SessionExpiredController.onPageLoad()

  def createView = () => btaQuestions(frontendAppConfig, form, action)(fakeRequest, messages)

  def createViewUsingForm =
    (form: Form[_]) => btaQuestions(frontendAppConfig, form, action)(fakeRequest, messages)

  "BTAQuestions view" must {
    behave like normalPage(createView, messageKeyPrefix, "intro1", "intro3")

    behave like optionsPage(
      createViewUsingForm,
      "mainService",
      MainServiceQuestion.options,
      "btaQuestions.mainService")

    behave like stringPage(
      createViewUsingForm,
      "mainServiceOther",
      "btaQuestions.mainService")

    behave like yesNoPage(
      createViewUsingForm,
      "ableToDo",
      "btaQuestions.ableToDo")

    behave like optionsPage(
      createViewUsingForm,
      "howEasyScore",
      HowEasyQuestion.options,
      "btaQuestions.howEasyScore")

    behave like stringPage(
      createViewUsingForm,
      "whyGiveScore",
      "btaQuestions.whyGiveScore")

    behave like optionsPage(
      createViewUsingForm,
      "howDoYouFeelScore",
      HowDoYouFeelQuestion.options,
      "btaQuestions.howDoYouFeelScore")

    "contain second introductory paragraph" in {
      val expectedMessage = messages("btaQuestions.intro2", messages("btaQuestions.introLinkText"))
      val doc = asDocument(createView())
      assertContainsText(doc, expectedMessage)
    }

    "contain privacy anchor tag" in {
      val expectedLink = messages("btaQuestions.introLinkText")
      val doc = asDocument(createView())
      assertContainsLink(doc, expectedLink, frontendAppConfig.privacyPolicyUrl)
    }
  }
}