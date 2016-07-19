/*
 * Copyright 2012 Victor Andreenko
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
/*global $:false */
/*global $:false */

$(document).ready(function () {
    $(function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $(document).ajaxSend(function (e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    });

    $('select.account-role').change(function () {
        var $this = $(this);

        $.getJSON(urlPrefix + "/ajax/changeRole",
            {
                accountId: $this.data('account'),
                role: $(this).find('option:selected').attr('value')
            },
            function (data) {
                if (data.newState == '1'){
                    $.growl({ title: "", message: data.info });
                } else {
                    $.growl({title:"Error", message: "You couldn't change the role"})
                }
            }
        );
    });

    $('.rate-item').click(function () {
        var $this = $(this);
        if ($this.data('state') == '0') {
            $.getJSON(urlPrefix + "/itemRate/add",
                {
                    item: $this.data('item'),
                    type: $this.data('type'),
                    state: $this.data('state'),
                    value: $this.data('value'),

                    rated: $this.data('rated'),
                    ratedup: $this.data('ratedup'),
                    rateddown: $this.data('rateddown'),
                    rating: $this.data('rating')
                },
                function (data) {
                    if (data.newState == '1') {
                        $this.data('state', data.newState);
                        $this.data('rated', data.newRated);
                        $this.data('ratedup', data.newRatedUp);
                        $this.data('rateddown', data.newRatedDown);
                        $this.data('rating', data.newRating);

                        $this.parent().children('button').addClass('disabled');
                        $this.parent().children('.rate-number').text(data.newRating);
                    }
                    $.growl({ title: "", message: data.info });
                }
            );
        } else {
        }
    });


    $('button.item-relation').click(function () {
        var $this = $(this);
        if ($this.data('state') == '1') {
            $.post(urlPrefix + "/itemRelation/delete.json",
                {
                    item: $this.data('item'),
                    type: $this.data('type'),
                    state: $this.data('state'),
                    subscribers: $this.data('subscribers')
                },
                function (data) {
                    if (data.newState == '0') {
                        $this.data('state', data.newState);
                        $this.data('subscribers', data.newSubscribers);

                        if ($this.data('type') == 'blog' || $this.data('type') == 'account') {
                            $this.removeClass('btn-primary');
                            $this.addClass('btn-default');
                            $this.html('<span class="glyphicon glyphicon-star-empty"></span> ' + data.label);
                        } else if ($this.data('type') == 'post' || $this.data('type') == 'comment') {
                            $this.html('<span class="glyphicon glyphicon-star-empty"></span> ' + data.newSubscribers);
                        }
                        $.growl({ title: "", message: data.info });
                    } else {
                    }
                }, "json"
            );
        } else {
            $.post(urlPrefix + "/itemRelation/add.json",
                {
                    item: $this.data('item'),
                    type: $this.data('type'),
                    state: $this.data('state'),
                    subscribers: $this.data('subscribers')
                },
                function (data) {
                    if (data.newState == '1') {
                        $this.data('state', data.newState);
                        $this.data('subscribers', data.newSubscribers);

                        if ($this.data('type') == 'blog' || $this.data('type') == 'account') {
                            $this.removeClass('btn-default');
                            $this.addClass('btn-primary');
                            $this.html('<span class="glyphicon glyphicon-star"></span> ' + data.label);
                        } else if ($this.data('type') == 'post' || $this.data('type') == 'comment') {
                            $this.html('<span class="glyphicon glyphicon-star"></span> ' + data.newSubscribers);
                        }
                        $.growl({ title: "", message: data.info });
                    } else {
                    }
                }, "json"
            );
        }
    });

});  // $(document).ready()